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
          <p>
            Is Admin: {user.authorities.some((value) => {
              return value.authority === 'ROLE_ADMIN';
            }) ? 'YES' : 'No'}
          </p>
        </div>
      ) : (
        <p>Đang tải dữ liệu user...</p>
      )}
    </div>
  );
});