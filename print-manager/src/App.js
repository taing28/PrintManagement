import { Route, Routes } from "react-router-dom";
import './App.css';
import { AuthPage, Delivery, Home, LayoutCompo, Login, Project, ProjectBill, ProjectDesign, ProjectDetail, ProjectPrint, Register, ResetPassword, Stock, Team, UpdatePassword, User } from "./components";
import { ProjectProvider } from "./components/config/ProjectContext";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/auth" element={<AuthPage />}>
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
          <Route path="reset-password" element={<ResetPassword />} />
          <Route path="update-password" element={<UpdatePassword />} />
        </Route>

        <Route path="/" element={<LayoutCompo />} >
          <Route index element={<Home />} />
          <Route path="teams" element={<Team />} />
          <Route path="users" element={<User />} />
          <Route path="projects" element={<Project />} />
          <Route path="projects/:projectId" element={
            <ProjectProvider>
              <ProjectDetail />
            </ProjectProvider>
          } />
          <Route path="projects/:projectId/designs" element={
            <ProjectProvider>
              <ProjectDesign />
            </ProjectProvider>
          } />
          <Route path="projects/:projectId/prints" element={
            <ProjectProvider>
              <ProjectPrint />
            </ProjectProvider>
          } />
          <Route path="projects/:projectId/bill" element={
            <ProjectProvider>
              <ProjectBill />
            </ProjectProvider>
          } />
          <Route path="stocks" element={<Stock />} />
          <Route path="deliveries" element={<Delivery />} />
        </Route>
      </Routes>

    </div>
  );
}

export default App;
