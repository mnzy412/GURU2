import useUser from "@/hooks/useUser";
import dayjs from "dayjs";
import { Timestamp } from "firebase/firestore";

interface Props {
    id: string;
    text: string;
    user_id: string;
    date: Timestamp;
    onClick: (id: string) => void;
}

const MemoListItem: React.FC<Props> = (
    {
        id,
        text,
        user_id,
        date,
        onClick
    }
) => {

    const user = useUser(user_id);
    const dateText = dayjs(new Date(date.seconds * 1000)).format("MM/DD");

    return (
        <div key={id} onClick={() => onClick(id)} className="p-3 bg-white hover:bg-gray-100 transition-all shadow-lg rounded-md">
            <span className="block text-sm mb-3">{text}</span>
            <div className="flex flex-row items-center justify-between">
                <span className="text-sm text-[#A1816E]">
                    {dateText}
                </span>
                <span className="text-sm text-[#A1816E]">
                    {user?.nickname}
                </span>
            </div>
        </div>
    );
};

export default MemoListItem;