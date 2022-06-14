import React, {useState} from "react";
import "./SubmitQuestion.css"
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import {serverURL} from "../../serverURL";
import {loadingStatuses} from "../../loadingStatuses";


export default function SubmitQuestion({axios, units, topics, topicStatus}) {
    const [unit, setUnit] = useState('')
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
            case "unit":
                setUnit(e.target.value)
                break;
        }

    }

    const renderUnitRadioOptions = () => {
        if (topicStatus === loadingStatuses.isLoading) {
            return <>loading</>
        } else if (topicStatus === loadingStatuses.loadingFailed) {
            return <>failed to load</>
        } else {
            return (
                <FormControl>
                    <FormLabel id="unit-radio-buttons-group-label">Unit</FormLabel>
                    <RadioGroup
                        name="unit"
                        onChange={handleChange}
                    >
                        {units.map(unit => <FormControlLabel key={unit.unitEnum} value={unit.unitEnum} control={<Radio/>} label={unit.unit} />)}
                    </RadioGroup>
                </FormControl>
            );

        }
    }

    const onSubmit = () => {
        let reqBody = {
            unitEnum: unit,
            topicEnum: "SCALAR_AND_VECTOR_QUANTITIES",
            baseQuestion: question,
            solutionEquation: "1 + 2",
            solutionUnit: solutionUnit,
            variables: [],
        }
        console.log(reqBody)
        axios.post(`${serverURL}/questionTemplates`, reqBody)
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    //TODO: Delete when complete
    const listOfItemsToInclude = () => {
        return (
            <>
                <p>To submit a question you need the following items:</p>
                <ul>
                    <li>unitEnum</li>
                    <li>topicEnum - will need to change this to list of topics</li>
                    <li>baseQuestion</li>
                    <li>solutionEquation</li>
                    <li>solutionUnit</li>
                    <li>List of variables</li>
                </ul>
            </>
        )
    }

    return (
        <div>
            <div>
                <h2>Ask a Question</h2>
                {listOfItemsToInclude()}
            </div>
            <div className="form-container">
                <div className="unit-options-container">
                    {renderUnitRadioOptions()}
                </div>
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