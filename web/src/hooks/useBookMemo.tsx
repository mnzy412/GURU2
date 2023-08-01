import { MemoDTO } from "@/apis/dto/memo.dto";
import { db } from "@/utils/firebase";
import { collection, getDocs, orderBy, query, where } from "firebase/firestore";
import { useEffect, useState } from "react";

const useBookMemo = (isbn13: string) => {

    const [loading, setLoading] = useState<boolean>(false);
    const [data, setData] = useState<MemoDTO[]>([]);
    const [error, setError] = useState<any>(null);

    const fetchMemoList = async () => {
        try {
            setLoading(true);
            const kind = 'memo';

            const q = query(
                collection(db, kind),
                where("isbn13", "==", isbn13),
                orderBy("created_at", "desc")
            );
            const querySnapshot = await getDocs(q);
            const books = querySnapshot.docs.map((doc) => {
                return {
                    id: doc.id,
                    ...doc.data()
                }
            });
            setData(books as MemoDTO[]);
        } catch (e) {
            console.log(e);
            setError(e);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (!isbn13) return;

        fetchMemoList();
    }, [isbn13]);

    return {
        loading,
        data,
        error,
        refetch: fetchMemoList
    }
};

export default useBookMemo;