import { IcCheckBrown, IcClose } from '@/assets/icons';
import { ImgAcron } from '@/assets/images';
import useBookshelfStore from '@/store/bookshelf.store';
import { db } from '@/utils/firebase';
import dayjs from 'dayjs';
import { Timestamp, doc, updateDoc } from 'firebase/firestore';
import { useEffect, useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import toast from 'react-hot-toast';
import { BottomSheet } from 'react-spring-bottom-sheet';
import 'react-spring-bottom-sheet/dist/style.css';

export type TileArgs = {
    activeStartDate: Date;
    date: Date;
};

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];


export default function CalendarTab() {

    const { data, setData } = useBookshelfStore();
    const [open, setOpen] = useState(false);
    const [value, onChange] = useState<Value>(new Date());

    const [selectedDay, setSelectedDay] = useState(new Date());
    const [readAmount, setReadAmount] = useState(0);

    const isToday = (date: Date) => {
        const today = new Date();
        return date.getDate() === today.getDate() && date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear();
    };
    const tileClassName = ({ date }: TileArgs) => isToday(date) ? 'today' : null;
    const formatDay = (locale: string | undefined, date: Date) => new Intl.DateTimeFormat(locale, { day: 'numeric' }).format(date);

    const handleClickDay = (value: Date) => {
        setSelectedDay(value);
        setOpen(true);
    };

    const handleConfirmChangeAmount = async () => {
        const bookshelfRef = doc(db, "bookshelf", data.id);
        const array = [...data.read_history];

        const indexToUpdate = array.findIndex((history) => history.datetime.seconds === selectedDay.getTime() / 1000);
        if (indexToUpdate === -1) {
            array.push({
                datetime: Timestamp.fromDate(selectedDay),
                amount: readAmount
            });
        } else {
            array[indexToUpdate] = {
                datetime: array[indexToUpdate].datetime,
                amount: readAmount
            }
        }

        // TODO: 아래 두개 함수 동시에 실행하는 유틸 함수 만들어야함.
        await updateDoc(bookshelfRef, {
            read_history: array
        });
        setData({ read_history: array });

        setOpen(false);
        toast.success('독서 기록을 성공적으로 저장했어요! 📚')
        setReadAmount(0);
    };

    const handleChangeReadAmount = (e: React.ChangeEvent<HTMLInputElement>) => {
        const valueAsNumber = parseInt(e.target.value, 10);
        setReadAmount(isNaN(valueAsNumber) ? 0 : valueAsNumber);
    };

    useEffect(() => {
        const day = data.read_history.filter((history) => {
            const historyDate = dayjs(new Date(history.datetime.seconds * 1000)).format('YYYY-MM-DD');
            const compareDate = dayjs(selectedDay).format('YYYY-MM-DD');
            return historyDate === compareDate;
        });
        if (day.length > 0) {
            setReadAmount(day[0].amount ?? 0);
        }
    }, [selectedDay]);


    return (
        <div className="w-full h-full bg-[#EFEBE5]">
            <Calendar
                onChange={onChange}
                value={value}
                locale='ko-KR'
                showNeighboringMonth={false}
                formatDay={formatDay}
                formatMonthYear={(_, date) => new Intl.DateTimeFormat('ko-KR', { year: 'numeric', month: 'long' }).format(date)}
                formatShortWeekday={(_, date) => new Intl.DateTimeFormat('ko-KR', { weekday: 'short' }).format(date)}
                tileContent={({ date, view }) => {
                    const isRead = data.read_history.find((history) => {
                        const historyDate = dayjs(new Date(history.datetime.seconds * 1000)).format('YYYY-MM-DD');
                        const compareDate = dayjs(date).format('YYYY-MM-DD');
                        return historyDate === compareDate;
                    });
                    if (view === 'month') {
                        if (isRead && isRead.amount > 0) {
                            return <img className='mt-1 w-4 mx-auto' src={ImgAcron} />;
                        } else {
                            return <div className='w-4 h-[25px]' />
                        }
                    }
                }}
                tileClassName={tileClassName}
                onClickDay={handleClickDay}
            />

            <BottomSheet
                className='w-full'
                header={false}
                open={open}>
                <div className='flex flex-row px-5 py-4 items-center justify-between'>
                    <img src={IcClose} className='w-6 h-6' onClick={() => setOpen(false)} />
                    <span className='text-lg text-[#654D47] font-bold'>읽은 페이지 저장하기</span>
                    <img src={IcCheckBrown} className='w-6 h-6' onClick={handleConfirmChangeAmount} />
                </div>
                <div className='my-12'>
                    <input onChange={handleChangeReadAmount} value={readAmount} autoFocus className='text-[#654D47] text-8xl block focus:outline-none mx-auto text-center w-full' type='number' />
                </div>
            </BottomSheet>
        </div>
    );
};