import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
    apiKey: "AIzaSyAGHby6LTvq-H800hj7cV_whX_0n-C7o04",
    authDomain: "booktory728.firebaseapp.com",
    databaseURL: "https://booktory728-default-rtdb.firebaseio.com",
    projectId: "booktory728",
    storageBucket: "booktory728.appspot.com",
    messagingSenderId: "278608554556",
    appId: "1:278608554556:web:b04d35f8481fc79093f139",
    measurementId: "G-3VSDCF4BCP"
};

const app = initializeApp(firebaseConfig);

export const db = getFirestore(app);
export const realtimeDB = getDatabase(app);
export const storage = getStorage(app);