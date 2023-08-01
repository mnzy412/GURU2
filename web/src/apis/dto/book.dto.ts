import { Timestamp } from "firebase/firestore";

/**
 * This BookDTO works with firebase firestore databases.
 * (Native mode)
 */
export interface BookDTO {
    id: string; // Firebase firestore document id (auto-generated), Not a UUID type!!
    title: string;
    author: string;
    book_image: string;
    rating: number;
    publisher: string;
    publication_year: number
    created_at: Timestamp;
}