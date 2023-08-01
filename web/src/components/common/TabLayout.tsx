import { useState } from "react";

interface TabLayoutProps {
    tabs: Tab[];
}

export interface Tab {
    id: string; // calendar, readtime, memo .. etc
    children: React.ReactNode;
}

const TabLayout: React.FC<TabLayoutProps> = ({ tabs }) => {

    const [selectedTab, setSelectedTab] = useState<string>(tabs[0].id);

    const renderChildren = () => {
        const tab = tabs.find((tab) => tab.id === selectedTab);
        if (tab) {
            return tab.children;
        }
        return null;
    };

    return (
        <div className="h-full">
            <div className="flex flex-row space-x-2 mx-5">
                {
                    tabs.map((tab) => (<TabItem
                        key={tab.id}
                        id={tab.id}
                        text={tab.id}
                        selected={selectedTab === tab.id}
                        onClickTabItem={(id) => setSelectedTab(id)}
                    />
                    ))
                }
            </div>
            {renderChildren()}
        </div>
    );
};

interface TabItemProps {
    id: string;
    text: string; // title
    selected: boolean;
    onClickTabItem: (id: string) => void;
}

const TabItem: React.FC<TabItemProps> = (
    {
        id,
        text,
        selected,
        onClickTabItem
    }
) => {

    const textColor = selected ? '#EFEBE5' : '#654D47';
    const bgColor = selected ? '#654D47' : '#EFEBE5';

    return (
        <div
            onClick={() => onClickTabItem(id)}
            style={{ background: bgColor }}
            className="rounded-tl-lg rounded-tr-lg px-3 py-1">
            <span style={{ color: textColor }}>
                {text}
            </span>
        </div>
    );
};

export default TabLayout;