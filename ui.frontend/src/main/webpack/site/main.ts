import A11y from "./js/a11y.js";
import "./main.scss"
// Javascript or Typescript
import { Initializer } from "../framework/initializer";
import Analytics from "../components/common/main";
import UnbouncePage from "../components/unbounce-pages/unbounce-page";

new Initializer();
new Analytics();
new A11y();
new UnbouncePage();
