import React from "react";

interface Props {
    loading: boolean;
    onClickConfirm: () => void;
    onClickCancel?: () => void;
}

const BookSelectConfirmModal: React.FC<Props> = (
    {
        loading,
        onClickConfirm,
        onClickCancel,
    }
) => {
    return (
        <div
            className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-end">
            <div className="p-6 w-full mb-8">
                <button
                    className="mb-2 h-10 block w-full rounded-md bg-[#EFEBE5E5] hover:bg-[#ddd8d1e5] active:bg-[#ddd8d1e5] transition-all px-4 py-2 text-[#191919]"
                    onClick={onClickCancel}
                >
                    취소
                </button>
                <button
                    className="block h-10 w-full rounded-md bg-[#EFEBE5E5] hover:bg-[#ddd8d1e5] active:bg-[#ddd8d1e5] transition-all px-4 py-2 text-[#191919]"
                    onClick={e => {
                        e.stopPropagation();
                        e.nativeEvent.stopPropagation();
                        onClickConfirm();
                    }}
                >
                    {!loading ? "책장에 추가하기" : "책장에 추가하는 중 ..."}
                </button>
            </div>
        </div>
    );
};

export default BookSelectConfirmModal;