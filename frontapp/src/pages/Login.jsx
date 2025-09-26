export default function Login () {
    return (
        <>
            <h1>로그인</h1>
            <form action="">
                <label>
                    <span>로그인 아이디: </span>
                    <input type="text" />
                </label>
                <br />
                <label>
                    <span>로그인 비밀번호: </span>
                    <input type="password" />
                </label>
                <button>로그인</button>
            </form>
        </>
    )
}