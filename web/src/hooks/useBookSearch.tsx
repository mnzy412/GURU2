import axios from "axios";
import { useEffect, useState } from "react";

export interface KakaoBookDTO {
    authors: string[];
    contents: string;
    datetime: string;
    isbn: string;
    price: number;
    publisher: string;
    sale_price: number;
    status: string;
    thumbnail: string;
    title: string;
    translators: string[];
    url: string;
}

interface BooksResponse {
    documents: KakaoBookDTO[];
    meta: {
        is_end: boolean;
        pageable_count: number;
        total_count: number;
    };
}

const URL = "https://dapi.kakao.com/v3/search/book?target=title";

const headers = {
    Authorization: `KakaoAK ${import.meta.env.VITE_APP_KAKAO_API_KEY ?? ""}`
};

const useBookSearch = (query: string) => {

    const [loading, setLoading] = useState<boolean>(false);
    const [data, setData] = useState<KakaoBookDTO[]>([]);
    const [error, setError] = useState<any>(null);

    useEffect(() => {
        if (!query) return;

        try {
            const fetchData = async () => {
                setLoading(true);
                const url = URL + `&query=${query}`;
                const { data } = await axios.get<BooksResponse>(url, { headers });
                setData(data.documents);
                setLoading(false);
            };
            fetchData();
        } catch (e) {
            setError(e);
        }
    }, [query]);

    return {
        loading,
        data,
        error
    }
};

export default useBookSearch;