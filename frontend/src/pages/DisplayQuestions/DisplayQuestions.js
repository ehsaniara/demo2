export default function DisplayQuestions({questions}) {
    const renderQuestions = () => {
        console.log(questions)
        if (questions.length === 0) return <h2>Submit a question</h2>
        return (
            <>
                <h2>Pick a question to try or submit a new question</h2>
                {questions.map(question => (
                        <div key={question.questionTemplateUuid}>
                            <p>
                                {question.baseQuestion}
                            </p>
                        </div>
                    )
                )
                }
            </>
        )
    }

    return (
        <div>
            {renderQuestions()}
        </div>
    )

}