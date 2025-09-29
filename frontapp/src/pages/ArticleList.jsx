import { useEffect, useState } from 'react'

function ArticleList () { // 짧을 때 사용
    const [articleList, setArticleList] = useState([])

    useEffect(() => {
        fetch("http//localhost:8090/api/v1/aricles")
            .then((res) => res.json())
            .then((res) => {
                console.log(res.data.articleList)
                setArticleList(res.data.articleList)
            })
    }, [])

    return (
        <>
            <ul>
                {articleList.map((article) => (
                    <li key={article.id}>
                        <span>{article.id}</span>
                        <span>{article.subject}</span>
                        <span>{article.content}</span>
                        <span>{article.author}</span>
                    </li>
                ))}
            </ul>
        </>
    )
}
export default ArticleList