import React from "react";
import { Nav, NavLink, NavMenu } from "./NavbarElements";

const Navbar = () => {
    return (
        <>
            <Nav>
                <NavMenu>
                    <NavLink to="/">
                        About
                    </NavLink>
                    <NavLink to="/search">
                        Search
                    </NavLink>
                    <NavLink to="/submitQuestion">
                        Submit Question
                    </NavLink>
                    <NavLink to="/attemptSolution">
                        Attempt Solution
                    </NavLink>
                </NavMenu>
            </Nav>
        </>
    );
};

export default Navbar;