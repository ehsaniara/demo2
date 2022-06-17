import React, {useEffect, useState} from "react";
import "./SubmitQuestion.css"
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Checkbox from '@mui/material/Checkbox';
import {serverURL} from "../../serverURL";
import {loadingStatuses} from "../../loadingStatuses";

import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';


export default function SubmitQuestion({axios, units, topics, topicStatus}) {
    const [questionUnit, setQuestionUnit] = useState('')
    const [topicList, setTopicList] = useState([])
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
                setQuestionUnit(e.target.value)
                break;
            case "topic":
                if (e.target.checked) {
                    setTopicList([...topicList, {topicUuid: e.target.value, topicEnum: e.target.id}])
                } else {
                    let checkedTopics = topicList.filter(value => {
                        return e.target.id !== value.topicEnum
                    })
                    setTopicList(checkedTopics)
                }
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
                <FormControl fullWidth={true}>
                    <FormLabel id="unit-radio-buttons-group-label">Physics Unit with Topics</FormLabel>
                    <RadioGroup
                        name="unit"
                        onChange={handleChange}
                    >
                        {units.map(unit => (
                            <div key={unit.unitEnum}>
                                <Accordion>
                                    <div className="accordion-unit">
                                        <FormControlLabel
                                            value={unit.unitEnum}
                                            control={<Radio/>}
                                            label={unit.unit}
                                            className="accordion-unit-label"
                                        />
                                        <AccordionSummary
                                            expandIcon={<ExpandMoreIcon/>}
                                            aria-controls="panel1a-content"
                                            id="panel1a-header"
                                        >
                                        </AccordionSummary>
                                    </div>
                                    <AccordionDetails>
                                        <div className="topics-container">
                                            {topics
                                                .filter(topic => topic.unitEnum === unit.unitEnum)
                                                .map(topic =>
                                                    <div key={topic.topicEnum}>
                                                        <Checkbox id={topic.topicEnum} value={topic.topicUuid}
                                                                  name="topic"
                                                                  onChange={handleChange}/>
                                                        {topic.topic}
                                                    </div>
                                                )}
                                        </div>
                                    </AccordionDetails>
                                </Accordion>
                            </div>
                        ))}
                    </RadioGroup>
                </FormControl>
            );
        }
    }

    const onSubmit = () => {
        let reqBody = {
            unitEnum: questionUnit,
            topicEntityList: topicList,
            baseQuestion: question,
            solutionEquation: "1 + 2",
            solutionUnit: solutionUnit,
            variables: [],
        }
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
                    <li>solutionEquation</li>
                    <li>List of variables</li>
                </ul>
            </>
        )
    }

    return (
        <div className="submit-question-body">
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