import { useState } from 'react'

export default function ArticleList () { // 짧을 때 사용
    const [ArticleList, setArticleList] = useState()

    fetch("http//localhost:8090/api/v1/aricles")
        .then((res) => res.json())
        .then((res) => {
            console.log(res)
        })

    return (
        <>
            <ul>
                {ArticleList.map((article) => (
                    <li key={article.id}>{article.subject}</li>
                ))}
            </ul>
        </>
    )
}