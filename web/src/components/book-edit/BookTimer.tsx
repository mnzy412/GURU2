import useBookshelfStore from '@/store/bookshelf.store';
import { db } from '@/utils/firebase';
import { doc, updateDoc } from 'firebase/firestore';
import React, { useState, useRef } from 'react';

const BookTimer: React.FC = () => {

    const { data, setData } = useBookshelfStore();
    const [time, setTime] = useState(data.readtime);
    const [isRunning, setIsRunning] = useState(false);
    const timerRef = useRef<number | null>(null);

    const saveTime = async (reset?: boolean) => {
        console.log('saveTime', time, data.id)
        const bookshelfRef = doc(db, "bookshelf", data.id);
        await updateDoc(bookshelfRef, {
            readtime: reset ? 0 : time,
        });
        setData({ readtime: reset ? 0 : time })
    };

    const formatTime = (timeInSeconds: number): string => {
        const hours = Math.floor(timeInSeconds / 3600);
        const minutes = Math.floor((timeInSeconds % 3600) / 60);
        const seconds = timeInSeconds % 60;
        return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    };

    const startTimer = () => {
        setIsRunning(true);
        timerRef.current = window.setInterval(() => {
            setTime((prevTime) => prevTime + 1);
        }, 1000);
        saveTime();
    };

    const pauseTimer = () => {
        setIsRunning(false);
        if (timerRef.current) {
            window.clearInterval(timerRef.current);
        }
        saveTime();
    };

    const resetTimer = () => {
        const confirm = window.confirm('정말 초기화 하시겠습니까?');
        if (confirm) {
            setIsRunning(false);
            setTime(0);
            if (timerRef.current) {
                window.clearInterval(timerRef.current);
            }
            saveTime(true);
        }
    };

    return (
        <div className="py-8 mx-auto">
            <div className="text-6xl font-bold text-[#191919] mb-16">
                {formatTime(time)}
            </div>
            <div className="mt-4 flex justify-center">
                {!isRunning && (
                    <button
                        className="text-2xl font-bold mr-2 px-4 py-2 bg-[#A1816E] text-white rounded"
                        onClick={startTimer}
                    >
                        시작
                    </button>
                )}
                {isRunning && (
                    <button
                        className="text-2xl font-bold mr-2 px-4 py-2 bg-[#A1816E] text-white rounded"
                        onClick={pauseTimer}
                    >
                        중지
                    </button>
                )}
                <button
                    className="text-2xl font-bold px-4 py-2 bg-gray-300 text-gray-700 rounded"
                    onClick={resetTimer}
                >
                    초기화
                </button>
            </div>
        </div>
    );
};

export default BookTimer;
