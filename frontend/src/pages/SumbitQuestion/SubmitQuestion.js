import {useState} from "react";
import "./SubmitQuestion.css"
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {serverURL} from "../../serverURL";


export default function SubmitQuestion({axios}) {
    const [question, setQuestion] = useState('')
    const [solutionUnit, setSolutionUnit] = useState('')

    const handleChange = (e) => {
        const name = e.target.name
        switch (name) {
            case "baseQuestion":
                setQuestion(e.target.value)
                break;
            case "solutionUnit":
                setSolutionUnit(e.target.value)
                break;
        }

    }

    const onSubmit = () => {
        let reqBody = {
            topicEnum: "SCALAR_AND_VECTOR_QUANTITIES",
            baseQuestion: question,
            solutionEquation: "1 + 2",
            solutionUnit: solutionUnit,
            variables:[],
        }
        console.log(reqBody)
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
                <div className="submit-question-text-field full-size">
                    <TextField
                        fullWidth
                        label="Question"
                        name="baseQuestion"
                        onChange={handleChange}
                    />
                </div>
                <div className="submit-question-text-field small-size">
                    <TextField
                        label="Solution Units"
                        name="solutionUnit"
                        onChange={handleChange}
                    />
                </div>
                <div className="submit-button small-size">
                    <Button variant="contained" onClick={onSubmit}>Submit</Button>
                </div>
            </div>
        </div>
    )

}