import { Timestamp } from "firebase/firestore";

/**
 * This BookDTO works with firebase firestore databases.
 * (Native mode)
 */
export interface MemoDTO {
    id: string; // Firebase firestore document id (auto-generated), Not a UUID type!!
    user_id: string;
    isbn13: string;
    /**
     * @deprecated
     */
    audio_url: string; // firebase storage url (audio)
    image_url: string; // firebase storage url (image)
    title: string;
    description: string;
    created_at: Timestamp;
}