import * as React from 'react'
import {Link} from "react-router-dom";
import {useState} from "react";
import {useNavigate} from "react-router";
import {useMySystem} from "../service/mySystem";

export const RegisterPage = () => {

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [errorMsg, setErrorMsg] = useState(undefined)
    const navigate = useNavigate();
    const mySystem = useMySystem();

    const handleSubmit = async e => {
        e.preventDefault();
        registerUser({
            email: username,
            password: password
        })
    }

    const resetForm = () => {
        setUsername('')
        setPassword('')
    }

    const registerUser = (user) => {
        mySystem.register(
            user,
            () => navigate("/login?ok=true"),
            () => {
                setErrorMsg('User already exists!')
                resetForm();
            }
        )
    }

    const usernameChange = (event) => {
        setUsername(event.target.value)
    }

    const passwordChange = (event) => {
        setPassword(event.target.value)
    }

    return (
        <div>
            {errorMsg && <div className="alert alert-danger" role="alert">{errorMsg}</div>}
            <form onSubmit={handleSubmit}>
                <h1>Sign up</h1>
                <div>
                    <input type="email"
                           placeholder="name@example.com"
                           value={username}
                           name="email"
                           onChange={usernameChange}/>
                </div>

                <div>
                    <input type="password"
                           id="floatingPassword"
                           placeholder="Password"
                           name="password"
                           value={password}
                           onChange={passwordChange}/>
                </div>

                <div>
                    <button type="submit">Sign up</button>
                </div>

                <div>
                    <Link to="/login">Sign In</Link>
                </div>
            </form>
        </div>
    )
}
