export default function Register() {
    return (
        <div className="signInForm">
            <form className="flex flex-col text-center" action="/login" method="POST">
                <h2>Register</h2>

                <label htmlFor="username">Username</label>
                <input type="username" id="username" name="username" placeholder="Enter your username" required />

                <label htmlFor="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required />

                <button type="submit">Sign Up</button>

                <div className="extra-links">
                    <a href="#">Already have an account</a>
                </div>
            </form>
        </div>
    )
}