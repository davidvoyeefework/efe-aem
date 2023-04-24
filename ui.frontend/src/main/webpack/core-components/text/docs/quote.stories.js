import Handlebars from "handlebars/runtime.js";
import Quote from "../quote.hbs";
Handlebars.registerPartial("Quote", Quote);
export default {
  title: "Core Components/Text",
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["quote1", "quote2"],
      control: { type: "radio" },
    }
  },
};

const QuoteTemplate = ({ label, ...args }) => Quote({ ...args });
export const Quotes = QuoteTemplate.bind();
Quotes.args = {
  variant: "quote1",
  text:"Sed egestas egestas fringilla phasellus faucibus scelerisque. Etiam sit amet nisl purus in mollis nunc sed id. Bibendum neque egestas commodo."
}

