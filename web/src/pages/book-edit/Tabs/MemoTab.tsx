import { IcPlus } from "@/assets/icons";
import MemoListItem from "@/components/book-edit/MemoListItem";
import useBookMemo from "@/hooks/useBookMemo";
import useBookshelfStore from "@/store/bookshelf.store";
import { useState } from "react";
import AddMemoModal from "../AddMemoModal";
import MemoDetailModal from "../MemoDetailModal";

export default function MemoTab() {

    const { data } = useBookshelfStore();
    const {
        data: memoList,
        refetch
    } = useBookMemo(data.isbn13);

    const [selectedMemo, setSelectedMemo] = useState<string>('');

    // memo related modals
    const [addModal, setAddModal] = useState(false);
    const [modalDetail, setModalDetail] = useState(false);

    const handleClickMemo = (id: string) => {
        setSelectedMemo(id);
        setModalDetail(true);
    }

    const handleClickAddMemo = () => setAddModal(true);
    const handleClickModalConfirm = () => {
        refetch();
        setAddModal(false);
        setModalDetail(false);
    }

    return (
        <div className="relative h-full w-full pb-10 bg-[#EFEBE5]">
            <div className="container space-y-3 max-w-2xl mx-auto py-8 px-5">
                {
                    memoList.length === 0 &&
                    <div className="my-20 mx-auto text-xl font-bold text-center text-slate-800">
                        👀
                        <br />
                        이 책에 작성된 메모가 없어요
                    </div>
                }
                {
                    memoList.map((memo) => (
                        <MemoListItem
                            key={memo.id}
                            id={memo.id}
                            text={memo.description}
                            user_id={memo.user_id}
                            date={memo.created_at}
                            onClick={() => handleClickMemo(memo.id)}
                        />
                    ))
                }
            </div>

            <div className="cursor-pointer fixed bottom-0 right-0 p-3 m-6 rounded-full bg-[#A1816E] shadow-xl">
                <img
                    onClick={handleClickAddMemo}
                    className="w-10 h-10"
                    src={IcPlus} />
            </div>

            {/* Modals */}
            {
                addModal && <AddMemoModal
                    onClickCancel={() => setAddModal(false)}
                    onClickConfirm={handleClickModalConfirm}
                />
            }
            {
                modalDetail && <MemoDetailModal
                    memo_id={selectedMemo}
                    onClickCancel={() => setModalDetail(false)}
                    onClickConfirm={handleClickModalConfirm}
                />
            }
        </div>
    )
}