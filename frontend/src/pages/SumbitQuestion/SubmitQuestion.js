import {useState} from "react";
import "./SubmitQuestion.css"
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {serverURL} from "../../serverURL";


export default function SubmitQuestion({axios}) {
    const [question, setQuestion] = useState("")

    const handleChangeQuestion = (e) => {
        setQuestion(e.target.value)
    }

    const onSubmit = () => {
        let reqBody = {
            topicEnum: "SCALAR_AND_VECTOR_QUANTITIES",
            baseQuestion: question,
            solutionEquation: "1 + 2",
            solutionUnit: "units",
            variables:[],
        }
        console.log(question)
        axios.post(`${serverURL}/questionTemplates`, reqBody)
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    return (
        <div>
            <div>
                <h2>Ask a Question</h2>
            </div>
            <div className="form-container">
                <div>
                    <TextField
                        fullWidth
                        label="Question"
                        id="fullWidth"
                        onChange={handleChangeQuestion}
                    />
                </div>
                <div>
                    <Button variant="contained" onClick={onSubmit}>Submit</Button>
                </div>
            </div>
        </div>
    )

}