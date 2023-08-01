import { AuthContext, AuthContextType } from "@/context/AuthProvider";
import useBookSearch, { KakaoBookDTO } from "@/hooks/useBookSearch";
import { useContext, useState } from "react";
import { IcBackArrow, IcSearch } from "../../assets/icons";
import BookItem from "../../components/book-search/BookItem";
import useDebounce from "../../hooks/useDebounce";
import BookSelectConfirmModal from "./BookSelectConfirmModal";
import { BookshelfDTO } from "@/apis/dto/bookshelf.dto";
import { addDoc, collection, getDocs, query, where } from "firebase/firestore";
import { db } from "@/utils/firebase";

const addBooksToShelf = async (userUid: string, kakaoBooks: KakaoBookDTO[]) => {
    const addKakaoBook = async (kakaoBook: KakaoBookDTO): Promise<string> => {
        const isbn13 = kakaoBook.isbn.split(" ")[1];

        // 책 기존에 있는지 체크
        const q = query(
            collection(db, "bookshelf"),
            where("user_id", "==", userUid),
            where("isbn13", "==", isbn13)
        );
        const querySnapshot = await getDocs(q);
        if (querySnapshot.size > 0) return "";

        // 책 추가
        const { authors, contents, title, publisher, datetime, thumbnail } = kakaoBook;
        const bookshelfData: Omit<BookshelfDTO, "id"> = {
            authors,
            book_contents: contents,
            book_title: title,
            isbn13,
            published_at: datetime,
            publisher,
            user_id: userUid,
            read_history: [],
            readtime: 0,
            thumbnail,
            rating: 0,
        };

        const docRef = await addDoc(collection(db, "bookshelf"), bookshelfData);
        console.log("Document written with ID: ", docRef.id);
        return docRef.id;
    };

    const bookIds = await Promise.all(kakaoBooks.map(addKakaoBook));
    return bookIds;
}

export default function BookSearch() {

    const { userUid } = useContext(AuthContext) as AuthContextType;

    const [modal, setModal] = useState(false);
    const [searchKeyword, setSearchKeyword] = useState<string>("");
    const debouncedSearchText = useDebounce<string>(searchKeyword, 500);

    // bookshelf 추가 중인지 체크
    const [addingBookShelf, setAddingBookShelf] = useState(false);

    const {
        data: bookList,
    } = useBookSearch(debouncedSearchText);

    const [selectedBooks, setSelectedBooks] = useState<KakaoBookDTO[]>([]);

    const isSelecting = selectedBooks.length > 0;

    const handleClickBack = () => (window as any).AndroidGuru2.close();

    const handleLongPressBookItem = (book: KakaoBookDTO) => {
        const index = selectedBooks.findIndex((selectedBook) => selectedBook.isbn === book.isbn);
        if (index > -1) {
            const newSelectedBooks = [...selectedBooks];
            newSelectedBooks.splice(index, 1);
            setSelectedBooks(newSelectedBooks);
        } else {
            setSelectedBooks([...selectedBooks, book]);
        }
    };

    const handleClickFinishSelect = () => {
        isSelecting && setModal(true);
    };

    const handleClickModalConfirm = async () => {
        setAddingBookShelf(true);
        await addBooksToShelf(userUid, selectedBooks);
        setAddingBookShelf(false);
        setModal(false);
        (window as any).AndroidGuru2.close();
    };

    return (
        <div className="container max-w-2xl mx-auto">
            <div className="flex flex-row items-center px-4 my-4">
                <img onClick={handleClickBack} className="w-6 h-6 mr-4" src={IcBackArrow} />
                <div className="flex flex-row flex-1 bg-[#EFEBE5] rounded-full items-center focus:outline-none">
                    <input
                        className="ml-4 my-2 block flex-1 bg-transparent focus:outline-none"
                        type="text"
                        placeholder="검색어를 입력하세요"
                        onChange={(e) => setSearchKeyword(e.target.value)}
                    />
                    <img className="w-4 h-4 my-2 mx-4" src={IcSearch} />
                </div>
            </div>
            <div className="mx-4 mt-8 mb-1 flex flex-row items-center justify-between">
                <span className="text-sm text-slate-800">
                    총 {bookList.length}권
                </span>
                <span onClick={handleClickFinishSelect} className="text-sm text-slate-800">
                    {isSelecting ? "선택 완료" : "선택하기"}
                </span>
            </div>
            {
                bookList.length === 0 &&
                <div className="my-20 mx-auto text-xl font-bold text-center text-slate-800">
                    👀
                    <br />
                    추가할 책을 검색해주세요
                </div>
            }
            <div className="grid grid-cols-3 gap-4 p-4">
                {
                    bookList.map((book) => (
                        <BookItem
                            key={book.isbn}
                            isSelected={selectedBooks.findIndex((selectedBook) => selectedBook.isbn === book.isbn) > -1}
                            book={book}
                            onClick={() => handleLongPressBookItem(book)}
                            onLongPress={() => handleLongPressBookItem(book)}
                        />
                    ))
                }
            </div>

            {
                modal &&
                <BookSelectConfirmModal
                    loading={addingBookShelf}
                    onClickCancel={() => setModal(false)}
                    onClickConfirm={handleClickModalConfirm}
                />
            }
        </div>
    )
};