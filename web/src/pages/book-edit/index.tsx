import { getBookShelfDetail } from "@/apis/firestore";
import { IcBackArrow, IcDelete } from "@/assets/icons";
import Rating from "@/components/book-edit/Rating";
import TabLayout, { Tab } from "@/components/common/TabLayout";
import useBookshelfStore from "@/store/bookshelf.store";
import { db } from "@/utils/firebase";
import { deleteDoc, doc, updateDoc } from "firebase/firestore";
import { Fragment, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import CalendarTab from "./Tabs/CalendarTab";
import MemoTab from "./Tabs/MemoTab";
import ReadTimeTab from "./Tabs/ReadTimeTab";
import { Dialog, Transition } from "@headlessui/react";

const tabConfiguration: Tab[] = [
    {
        id: '읽은 날짜',
        children: <CalendarTab />
    },
    {
        id: '읽은 시간',
        children: <ReadTimeTab />
    },
    {
        id: '기록',
        children: <MemoTab />
    }
];

export default function BookEdit() {

    const params = useParams();
    const { data, setData } = useBookshelfStore();
    const [rate, setRate] = useState<number>(data.rating);

    const [isOpen, setIsOpen] = useState(false)

    const handleClickBack = () => closeWindow();

    const handleClickDelete = () => setIsOpen(true);

    const handleClickDeleteConfirm = async () => {
        const bookshelfRef = doc(db, "bookshelf", params.bookshelf_id!!);
        await deleteDoc(bookshelfRef);
        (window as any).AndroidGuru2.showToast("삭제되었습니다.");
        closeWindow();
    };

    /**
     * @param rate number (1~5)
     */
    const handleClickRating = async (rate: number) => {
        const ratingRef = doc(db, "bookshelf", params.bookshelf_id!!);
        await updateDoc(ratingRef, {
            rating: rate
        });
        setRate(rate);
    };

    useEffect(() => {
        if (!params?.bookshelf_id) {
            closeWindow();
            return;
        }

        // Fetch bookshelf data
        (async () => {
            const bookshelf = await getBookShelfDetail(params.bookshelf_id!!);
            setRate(bookshelf.rating);
            setData(bookshelf);
        })();
    }, [params?.bookshelf_id]);

    return (
        <div className="h-full">
            <div className="flex flex-row items-center justify-between px-4 py-5">
                <img onClick={handleClickBack} className="w-6 h-6" src={IcBackArrow} />
                <img onClick={handleClickDelete} className="w-6 h-6" src={IcDelete} />
            </div>
            <div className="flex flex-row space-x-5 px-5 my-8 container max-w-2xl mx-auto">
                <img className="w-24 rounded-sm" src={data?.thumbnail} />
                <div className="flex flex-col flex-1 justify-between">
                    <div className="flex flex-col space-y-1">
                        <span className="text-xl font-bold text-[#191919]">{data?.book_title}</span>
                        {data?.authors.map((author, index) =>
                            <span key={index} className="text-lg text-[#565656]">{author}</span>
                        )}
                        <Rating onClick={handleClickRating} star={rate} />
                    </div>
                </div>
            </div>

            <TabLayout tabs={tabConfiguration} />

            <Transition appear show={isOpen} as={Fragment}>
                <Dialog as="div" className="relative z-10" onClose={() => setIsOpen(false)}>
                    <Transition.Child
                        as={Fragment}
                        enter="ease-out duration-300"
                        enterFrom="opacity-0"
                        enterTo="opacity-100"
                        leave="ease-in duration-200"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                    >
                        <div className="fixed inset-0 bg-black bg-opacity-25" />
                    </Transition.Child>

                    <div className="fixed inset-0 overflow-y-auto">
                        <div className="flex min-h-full items-center justify-center p-4 text-center">
                            <Transition.Child
                                as={Fragment}
                                enter="ease-out duration-300"
                                enterFrom="opacity-0 scale-95"
                                enterTo="opacity-100 scale-100"
                                leave="ease-in duration-200"
                                leaveFrom="opacity-100 scale-100"
                                leaveTo="opacity-0 scale-95"
                            >
                                <Dialog.Panel className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                                    <Dialog.Title
                                        as="h3"
                                        className="text-lg font-medium leading-6 text-gray-900"
                                    >
                                        이 책장 삭제하기
                                    </Dialog.Title>
                                    <div className="mt-2">
                                        <p className="text text-gray-500">
                                            이 책을 삭제하시겠어요?
                                            이 작업은 되돌릴 수 없어요.
                                        </p>
                                    </div>

                                    <div className="float-right flex flex-row space-x-2 mt-4">
                                        <button
                                            type="button"
                                            className="inline-flex justify-center rounded-md border border-transparent bg-[#EFEBE5] px-4 py-2 text-sm font-medium text-[#654D47] hover:bg-[#EFEBE5] focus:outline-none focus-visible:ring-2 focus-visible:ring-[#654D47] focus-visible:ring-offset-2"
                                            onClick={() => setIsOpen(false)}
                                        >
                                            닫기
                                        </button>
                                        <button
                                            type="button"
                                            className="inline-flex justify-center rounded-md border border-transparent bg-red-100 px-4 py-2 text-sm font-medium text-red-900 hover:bg-red-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-red-500 focus-visible:ring-offset-2"
                                            onClick={handleClickDeleteConfirm}
                                        >
                                            삭제하기
                                        </button>
                                    </div>
                                </Dialog.Panel>
                            </Transition.Child>
                        </div>
                    </div>
                </Dialog>
            </Transition>
        </div>
    );
};

/**
 * Android Interface
 * `finish()` 호출
 */
const closeWindow = () => {
    (window as any).AndroidGuru2.close();
};