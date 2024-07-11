import { Route, Routes } from "react-router-dom";
import './App.css';
import { AuthPage, Footer, Header, Home, Login, Register, ResetPassword, UpdatePassword } from "./components";

function App() {
  return (
    <div>
      <Routes>
          <Route path="/auth" element={<AuthPage/>}>
            <Route path="login" element={<Login/>}/>
            <Route path="register" element={<Register/>}/>
            <Route path="reset-password" element={<ResetPassword/>}/>
            <Route path="update-password" element={<UpdatePassword/>}/>
          </Route>
          <Route path="/" element={<>
            <Header />
            <Home />
            <Footer />
          </>} />
        </Routes>
        
    </div>
  );
}

export default App;
