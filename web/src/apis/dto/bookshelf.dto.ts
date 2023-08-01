import { Timestamp } from "firebase/firestore";

/**
 * This BookDTO works with firebase firestore databases.
 * (Native mode)
 */
export interface BookshelfDTO {
    id: string; // Firebase firestore document id (auto-generated), Not a UUID type!!
    authors: string[];
    book_contents: string;
    book_title: string;
    isbn13: string;
    published_at: string;
    publisher: string;
    user_id: string;
    read_history: BookReadHistoryDTO[];
    readtime: number; // 총 읽은 시간 기록
    thumbnail: string;
    rating: number;
}

export interface BookReadHistoryDTO {
    amount: number; // 당일 읽은 장 수 기록
    datetime: Timestamp; // 읽은 시간 기록
}