import { IcRoundStar, IcRoundStarFilled } from '@/assets/icons';
import React from 'react';

type RatingProps = {
  onClick: (rate: number) => void;
  star: number;
};

const Rating: React.FC<RatingProps> = ({ onClick, star }) => {
  const MAX_STARS = 5;

  const renderStars = () => {
    const filledStars = Math.min(Math.max(star, 1), MAX_STARS);

    const stars: React.ReactNode[] = [];
    for (let i = 1; i <= MAX_STARS; i++) {
      const isFilled = i <= filledStars;
      const starIcon = isFilled ? (
        <img src={IcRoundStarFilled} alt="filled star" className="w-8 h-8" />
      ) : (
        <img src={IcRoundStar} alt="empty star" className="w-8 h-8" />
      );

      stars.push(
        <span onClick={() => onClick(i)} key={i} className="inline-block mr-1">
          {starIcon}
        </span>
      );
    }
    return stars;
  };

  return <div className="flex">{renderStars()}</div>;
};

export default Rating;
