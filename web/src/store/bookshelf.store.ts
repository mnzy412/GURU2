import { BookshelfDTO } from "@/apis/dto/bookshelf.dto";
import { create } from "zustand";

const initData: BookshelfDTO = {
    id: "",
    authors: [],
    book_contents: "",
    book_title: "",
    isbn13: "",
    published_at: "",
    publisher: "",
    user_id: "",
    read_history: [],
    readtime: 0,
    thumbnail: "",
    rating: 0
};

type BookshelfStore = {
    data: BookshelfDTO;
    setData: (data: Partial<BookshelfDTO>) => void;
};

const useBookshelfStore = create<BookshelfStore>(
    (set) => ({
        data: initData,
        setData: (data: Partial<BookshelfDTO>) => set((state) => ({
            data: { ...state.data, ...data }
        })),
    }),
);

export default useBookshelfStore;