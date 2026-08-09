```jsx
import { createContext, useContext, useState } from 'react';
import { loginUser, registerUser } from '../services/authService';

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(() => {
        try {
            return JSON.parse(localStorage.getItem('blog_user') || 'null');
        } catch (error) {
            localStorage.removeItem('blog_user');
            localStorage.removeItem('blog_token');
            return null;
        }
    });

    const persist = (data) => {
        localStorage.setItem('blog_token', data.token);
        localStorage.setItem('blog_user', JSON.stringify(data.user));
        setUser(data.user);
    };

    const login = async (data) => {
        const response = await loginUser(data);
        persist(response);
        return response.user;
    };

    const register = async (data) => {
        const response = await registerUser(data);
        persist(response);
        return response.user;
    };

    const logout = () => {
        localStorage.removeItem('blog_token');
        localStorage.removeItem('blog_user');
        setUser(null);
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                login,
                register,
                logout,
                isRole: (...roles) => roles.includes(user?.role),
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
```
