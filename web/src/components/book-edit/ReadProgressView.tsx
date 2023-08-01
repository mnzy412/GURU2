import React from 'react';

type ProgressViewProps = {
    readProgress: number;
};

const ReadProgressView: React.FC<ProgressViewProps> = ({ readProgress }) => {
    const progressPercentage = Math.min(Math.max(readProgress, 0), 100);

    const progressBarStyles = {
        width: `${progressPercentage}%`,
    };

    return (
        <div className="flex-1 relative h-5 bg-[#EFEBE5] rounded-full">
            <div
                className="absolute top-0 left-0 h-full bg-[#C8B8A9] rounded-full flex items-center justify-center"
                style={progressBarStyles}
            >
                <span className='text-[#654D47]'>{readProgress}%</span>
            </div>
        </div>
    );
};

export default ReadProgressView;
