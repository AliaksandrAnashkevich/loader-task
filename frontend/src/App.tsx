import Main from "./pages/main/main";
import Auth from "./pages/auth/auth";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useEffect, useState } from "react";

export default function App() {
  const [isAuth, setIsAuth] = useState(false);

  useEffect(() => {
    setIsAuth(!!localStorage.getItem("token"));
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        {!isAuth ? (
          <Route path="/" element={<Auth setIsAuth={setIsAuth} />} />
        ) : (
          <Route path="/" element={<Main />} />
        )}
      </Routes>
    </BrowserRouter>
  );
}
