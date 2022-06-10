import { NavLink as Link } from "react-router-dom";
import styled from "styled-components";

export const Nav = styled.nav`
background: #184E2A;
height: 7vh;
display: flex;
justify-content: flex-start;
z-index: 12;
`;

export const NavLink = styled(Link)`
color: #FFE14D;
display: flex;
align-items: center;
text-decoration: none;
padding: 0 1em;
height: 100%;
cursor: pointer;
&.active {
	color: #0D87BF;
	transform: scale(1.2);
};
`;

export const NavMenu = styled.div`
display: flex;
align-items: center;
margin-left: 3em;
/* Second Nav */
/* margin-right: 24px; */
/* Third Nav */
/* width: 100vw;
white-space: nowrap; */
@media screen and (max-width: 768px) {
	display: none;
}
`;
