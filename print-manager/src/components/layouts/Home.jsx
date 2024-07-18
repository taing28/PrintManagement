import { memo } from "react";
import { useUser } from "../config/UserContext";

export const Home = memo(() => {
  const { user } = useUser();
  
  return (
    <div>
      {user && user.authorities ? (
        <div>
          <p>Roles: {user.authorities.map((value) => {
            return value.authority;
          }).join(", ")}</p>
        </div>
      ) : (
        <p>Đang tải dữ liệu user...</p>
      )}
    </div>
  );
});