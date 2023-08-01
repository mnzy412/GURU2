import { MemoDTO } from "@/apis/dto/memo.dto";
import { IcBackArrow, IcCamera, IcCheckBrown, IcImageDelete } from "@/assets/icons";
import { AuthContext, AuthContextType } from "@/context/AuthProvider";
import useBookshelfStore from "@/store/bookshelf.store";
import "@/styles/audio-recorder.css";
import { db, storage } from "@/utils/firebase";
import { Timestamp, addDoc, collection } from "firebase/firestore";
import { ref as createStorageRef, getDownloadURL, uploadBytes } from "firebase/storage";
import React, { useContext, useState } from "react";
import toast from "react-hot-toast";

interface Props {
    onClickConfirm: () => void;
    onClickCancel?: () => void;
}

const AddMemoModal: React.FC<Props> = (
    {
        onClickConfirm,
        onClickCancel,
    }
) => {

    const { data } = useBookshelfStore();
    const { userUid } = useContext(AuthContext) as AuthContextType;


    const [imageUploading, setImageUploading] = useState<boolean>(false);
    const [audioUploading, setAudioUploading] = useState<boolean>(false);

    const [title, setTitle] = useState<string>('');
    const [description, setDescription] = useState<string>('');

    const [recording, setRecording] = useState<Blob | null>(null);

    const [imageURL, setImageURL] = useState<string>('');
    const [audioURL, setAudioURL] = useState<string>('');

    // 이미지 파일 선택 시 호출되는 콜백 함수
    const handleImageSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            uploadImageToFirebase(file);
        }
    };

    // 마이크 녹음 파일을 Firebase Storage에 업로드하는 함수
    const uploadRecordingToFirebase = async (blob: Blob) => {
        const metadata = {
            contentType: 'audio/mp3'
        };

        try {
            setAudioUploading(true);
            const storageRef = createStorageRef(storage, 'recordings/' + Date.now() + '.mp3');
            await uploadBytes(storageRef, blob, metadata);
            const url = await getDownloadURL(storageRef);
            setAudioURL(url);
        } catch (error) {
            alert('마이크 녹음 업로드에 실패했습니다.');
            console.error(error);
        } finally {
            setAudioUploading(false);
        }
    };

    // 이미지 파일을 Firebase Storage에 업로드하는 함수
    const uploadImageToFirebase = async (file: File) => {
        if (!file) return;

        const metadata = {
            contentType: 'image/jpeg'
        };

        try {
            setImageUploading(true);
            const storageRef = createStorageRef(storage, 'images/' + file.name);
            await uploadBytes(storageRef, file, metadata);
            const url = await getDownloadURL(storageRef);
            setImageURL(url);
        } catch (error) {
            alert('이미지 업로드에 실패했습니다.');
            console.error(error);
        } finally {
            setImageUploading(false);
        }
    };

    const handleClickSubmit = async () => {
        if (!title || !description) return;

        const memoData: Omit<MemoDTO, "id"> = {
            user_id: userUid,
            isbn13: data.isbn13,
            audio_url: audioURL,
            image_url: imageURL,
            title,
            description,
            created_at: Timestamp.now(),
        };

        await addDoc(collection(db, "memo"), memoData);
        onClickConfirm();
        toast.success('메모를 성공적으로 적었어요!')
    };

    const addButtonOpacity = (title && description) ? 100 : 0.3;

    return (
        <div
            className="fixed top-0 left-0 w-full h-full bg-white">
            <div className='flex flex-row px-5 py-6 items-center justify-between'>
                <img src={IcBackArrow} className='w-5 h-5' onClick={onClickCancel} />
                <span className='text-[#654D47]'>기록</span>
                <img onClickCapture={handleClickSubmit} style={{ opacity: addButtonOpacity }} src={IcCheckBrown} className='w-5 h-5 text-red transition-all' onClick={onClickConfirm} />
            </div>
            <div className="px-5 py-3">
                <div className="flex flex-row items-center bg-[#EFEBE5] rounded-xl px-4 py-3 mb-3">
                    <input
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        type="text"
                        className='bg-transparent w-full ml-2 text-[#654D47] placeholder-[#654D47] focus:outline-none'
                        placeholder='제목을 입력해주세요' />
                </div>

                <div>
                    <textarea
                        value={description}
                        onChange={(e) => {
                            const value = e.target.value;
                            if (value.length > 200) return;
                            setDescription(e.target.value);
                        }}
                        placeholder="한 줄 평을 적어 주세요 :)"
                        className="resize-nonee block rounded-tl-xl rounded-tr-xl p-4 bg-[#EFEBE5] w-full text-[#654D47] placeholder-[#654D47] focus:outline-none min-h-[250px]"
                    />
                    <div className="flex flex-row items-center justify-between p-4 rounded-bl-xl rounded-br-xl bg-[#EFEBE5]">
                        <span className="self-end text-[#C8B8A9]">{description.length}/200</span>
                    </div>
                </div>

                <div onClick={() => {
                    const imageInput = document.getElementById('image-input');
                    imageInput?.click();
                }} className="mt-3 flex flex-row items-center px-5 py-3 rounded-lg border border-[#C8B8A9]">
                    <img src={IcCamera} className="w-5 h-5" />
                    <span className="ml-4 text-[#C8B8A9]">사진 첨부하기</span>
                    <input id="image-input" className="hidden" type="file" accept="image/*" onChange={handleImageSelect} />
                </div>

                {/* 오디오 업로드 */}
                {
                    audioUploading && (<span className="text-[#C8B8A9] font-bold">오디오 업로드 중 ...</span>)
                }
                {audioURL && (
                    <div className="my-3 flex flex-row items-center px-5 py-3 rounded-lg border border-[#C8B8A9]">
                        <div className="relative px-2 object-cover">
                            <img onClick={() => setAudioURL("")} src={IcImageDelete} className="absolute right-0 m-1" />
                            <audio id="memo-audio-player" controlsList="nodownload" preload="auto" controls>
                                <source src={audioURL} type="audio/mp3" />
                            </audio>
                        </div>
                    </div>
                )}

                {/* 이미지 업로드 */}
                {
                    imageUploading && (<span className="text-[#C8B8A9] font-bold">이미지 업로드 중 ...</span>)
                }
                {imageURL && (
                    <div className="mt-3 flex flex-row items-center px-5 py-3 rounded-lg border border-[#C8B8A9]">
                        <div className="relative w-[100px] h-[100px] px-2 shadow-xl object-cover">
                            <img onClick={() => setImageURL("")} src={IcImageDelete} className="absolute right-0 m-1" />
                            <img src={imageURL} className="w-full h-full" />
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default AddMemoModal;