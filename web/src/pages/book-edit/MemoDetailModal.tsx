import { MemoDTO } from "@/apis/dto/memo.dto";
import { getMemoById } from "@/apis/firestore";
import { IcBackArrow, IcDelete } from "@/assets/icons";
import { db } from "@/utils/firebase";
import { Dialog, Transition } from "@headlessui/react";
import { deleteDoc, doc } from "firebase/firestore";
import React, { Fragment, useEffect, useState } from "react";
import toast from "react-hot-toast";

interface Props {
    memo_id: string;
    onClickConfirm: () => void;
    onClickCancel: () => void;
}

const MemoDetailModal: React.FC<Props> = (
    {
        memo_id,
        onClickConfirm,
        onClickCancel,
    }
) => {

    const [memo, setMemo] = useState<MemoDTO | null>(null);
    const [isOpen, setIsOpen] = useState(false)

    const handleClickDelete = () => setIsOpen(true);

    const handleClickDeleteConfirm = async () => {
        if (!memo) return;
        const memoRef = doc(db, "memo", memo?.id);
        await deleteDoc(memoRef);
        toast.success("메모가 삭제되었어요. 🗑️");
        setIsOpen(false);
        onClickConfirm();
    };

    useEffect(() => {
        // setAudioRecorderImage();
        (async () => {
            const memo = await getMemoById(memo_id);
            setMemo(memo);
        })();
    }, []);

    if (!memo) return null;

    return (
        <div
            className="fixed top-0 left-0 w-full h-full bg-white">
            <div className='flex flex-row px-5 py-6 items-center justify-between'>
                <img src={IcBackArrow} className='w-5 h-5' onClick={onClickCancel} />
                <span className='text-[#654D47]'>기록</span>
                <img onClick={handleClickDelete} className="w-6 h-6" src={IcDelete} />
            </div>
            <div className="px-5 py-3">
                <div className="flex flex-row items-center bg-[#EFEBE5] rounded-xl px-4 py-3 mb-3">
                    <input
                        value={memo?.title}
                        readOnly
                        type="text"
                        className='bg-transparent w-full ml-2 text-[#654D47] placeholder-[#654D47] focus:outline-none'
                        placeholder='제목을 입력해주세요' />
                </div>

                <div>
                    <textarea
                        value={memo?.description}
                        readOnly
                        placeholder="한 줄 평을 적어 주세요 :)"
                        className="resize-nonee block rounded-tl-xl rounded-tr-xl p-4 bg-[#EFEBE5] w-full text-[#654D47] placeholder-[#654D47] focus:outline-none min-h-[250px]"
                    />
                    <div className="flex flex-row items-center justify-between p-4 rounded-bl-xl rounded-br-xl bg-[#EFEBE5]">
                        <span className="text-[#C8B8A9]">{memo.description.length}/200</span>
                    </div>
                </div>

                {memo.audio_url && <audio id="memo-audio-player" controlsList="nodownload" preload="auto" src={memo?.audio_url} controls className="w-full mt-3" />}
                {memo.image_url && <img className="rounded-xl shadow-lg mt-3" src={memo?.image_url} />}
            </div>


            <Transition appear show={isOpen} as={Fragment}>
                <Dialog as="div" className="relative z-10" onClose={() => setIsOpen(false)}>
                    <Transition.Child
                        as={Fragment}
                        enter="ease-out duration-300"
                        enterFrom="opacity-0"
                        enterTo="opacity-100"
                        leave="ease-in duration-200"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                    >
                        <div className="fixed inset-0 bg-black bg-opacity-25" />
                    </Transition.Child>

                    <div className="fixed inset-0 overflow-y-auto">
                        <div className="flex min-h-full items-center justify-center p-4 text-center">
                            <Transition.Child
                                as={Fragment}
                                enter="ease-out duration-300"
                                enterFrom="opacity-0 scale-95"
                                enterTo="opacity-100 scale-100"
                                leave="ease-in duration-200"
                                leaveFrom="opacity-100 scale-100"
                                leaveTo="opacity-0 scale-95"
                            >
                                <Dialog.Panel className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                                    <Dialog.Title
                                        as="h3"
                                        className="text-lg font-medium leading-6 text-gray-900"
                                    >
                                        이 메모 삭제하기
                                    </Dialog.Title>
                                    <div className="mt-2">
                                        <p className="text text-gray-500">
                                            이 메모를 삭제하시겠어요?
                                            이 작업은 되돌릴 수 없어요.
                                        </p>
                                    </div>

                                    <div className="float-right flex flex-row space-x-2 mt-4">
                                        <button
                                            type="button"
                                            className="inline-flex justify-center rounded-md border border-transparent bg-[#EFEBE5] px-4 py-2 text-sm font-medium text-[#654D47] hover:bg-[#EFEBE5] focus:outline-none focus-visible:ring-2 focus-visible:ring-[#654D47] focus-visible:ring-offset-2"
                                            onClick={() => setIsOpen(false)}
                                        >
                                            닫기
                                        </button>
                                        <button
                                            type="button"
                                            className="inline-flex justify-center rounded-md border border-transparent bg-red-100 px-4 py-2 text-sm font-medium text-red-900 hover:bg-red-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-red-500 focus-visible:ring-offset-2"
                                            onClick={handleClickDeleteConfirm}
                                        >
                                            삭제하기
                                        </button>
                                    </div>
                                </Dialog.Panel>
                            </Transition.Child>
                        </div>
                    </div>
                </Dialog>
            </Transition>
        </div>
    );
};

export default MemoDetailModal;