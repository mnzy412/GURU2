import { UserDTO } from "@/apis/dto/user.dto";
import { realtimeDB } from "@/utils/firebase";
import { get, ref } from "firebase/database";
import { useState } from "react";

const deleteUserForm: UserDTO = {
    email: "DELETE_USER@DELETE_USER",
    nickname: "삭제된 사용자",
    uid: "DELETE_USER"
};

const useUser = (user_id: string): UserDTO | undefined => {

    const [user, setUser] = useState<UserDTO>();

    const userRef = ref(realtimeDB, 'user/' + user_id);
    get(userRef).then((snapshot) => {
        if (snapshot.exists()) {
            setUser(snapshot.val());
        } else {
            setUser(deleteUserForm)
        }
    }).catch(() => {
        setUser(deleteUserForm)
    });

    return user;
};

export default useUser