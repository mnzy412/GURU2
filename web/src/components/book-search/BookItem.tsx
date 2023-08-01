import { KakaoBookDTO } from "@/hooks/useBookSearch";
import { useRef, useState } from "react";
import { IcCheck } from "../../assets/icons";

interface BookItemProps {
    book: KakaoBookDTO;
    isSelected?: boolean;

    // WARN: bookId만 넘겨줘야하지만 
    // DTO가 안 정해졌으므로 전체 객체를 다 넘겨줘야할듯;;
    onClick?: (book: KakaoBookDTO) => void;
    onLongPress: (book: KakaoBookDTO) => void;
}

const BookItem: React.FC<BookItemProps> = (
    {
        book,
        isSelected,
        onClick,
        onLongPress,
    }) => {

    const [_, setIsPressing] = useState(false);
    const pressTimer = useRef<ReturnType<typeof setTimeout> | null>(null);

    const handleMouseDown = () => {
        pressTimer.current = setTimeout(() => {
            setIsPressing(true);
            onLongPress(book);
        }, 500); // Change the duration as needed for your long-press threshold
    };

    const handleMouseUp = () => {
        if (pressTimer.current) {
            clearTimeout(pressTimer.current);
            pressTimer.current = null;
        }
        setIsPressing(false);
    };

    return (
        <div
            className="shadow-xl relative cursor-pointer hover:shadow-2xl transition-all duration-300"
            onClick={() => onClick && onClick(book)}
            onMouseDown={handleMouseDown}
            onMouseUp={handleMouseUp}
            onMouseLeave={handleMouseUp}
        >
            <img className="rounded-sm w-full object-cover" src={book.thumbnail} />

            {/* Overlay */}
            {
                isSelected &&
                <div className="absolute top-0 w-full h-full bg-[#0000003b] flex items-center justify-center">
                    <img src={IcCheck} />
                </div>
            }
        </div>
    );
};

export default BookItem;