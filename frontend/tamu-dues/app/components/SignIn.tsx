export default function Signin() {
    const loginURL = "http://localhost:8080/v1/login";
    return (
        <div className="signInForm">
            <form className="flex flex-col text-center" action={loginURL} method="POST">
                <h2>Sign In</h2>

                <label htmlFor="username">Username</label>
                <input type="username" id="username" name="username" placeholder="Enter your username" required />

                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required />

                <button type="submit">Sign In</button>

                <div className="extra-links">
                    <a href="#">Create an account</a>
                </div>
            </form>
        </div>
    )
}