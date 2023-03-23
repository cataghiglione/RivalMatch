import * as React from 'react'
import {Link} from "react-router-dom";

export const PublicPage = () => {
    return (
        <div>
            <ul>
                <li>
                    <Link to="/login">Sign in</Link>
                </li>
                <li>
                    <Link to="/register">Sign up</Link>
                </li>
            </ul>
        </div>
    )
}
