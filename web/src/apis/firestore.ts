import { db } from "@/utils/firebase";
import { collection, doc, getDoc, getDocs } from "firebase/firestore";
import { BookDTO } from "./dto/book.dto";
import { BookshelfDTO } from "./dto/bookshelf.dto";
import { MemoDTO } from "./dto/memo.dto";

/**
 * /book-search 페이지에서 사용됨
 */
export const getBookList = async (): Promise<BookDTO[]> => {
    const kind = 'book';

    const snapshot = await getDocs(collection(db, kind));
    const books = snapshot.docs.map(doc => ({
        id: doc.id,
        ...doc.data()
    }));

    return books as BookDTO[];
};

export const getMemoById = async (memo_id: string): Promise<MemoDTO> => {
    const kind = 'memo';
    console.log(memo_id)
    try {
        const docRef = doc(db, kind, memo_id);
        const docSnapshot = await getDoc(docRef);

        if (docSnapshot.exists()) {
            return {
                id: docSnapshot.id,
                ...docSnapshot.data()
            } as MemoDTO
        } else {
            throw new Error("No such document!");
        }
    } catch (error) {
        throw new Error("No such document!");
    }
};

/**
 * /book-edit 페이지에서 사용됨
 */
export const getBookShelfDetail = async (bookshelf_id: string): Promise<BookshelfDTO> => {
    const kind = 'bookshelf';

    try {
        const docRef = doc(db, kind, bookshelf_id);
        const docSnapshot = await getDoc(docRef);

        if (docSnapshot.exists()) {
            const data = docSnapshot.data();
            return {
                id: docSnapshot.id,
                ...data
            } as BookshelfDTO
        } else {
            throw new Error("No such document!");
        }
    } catch (error) {
        throw new Error("No such document!");
    }
};