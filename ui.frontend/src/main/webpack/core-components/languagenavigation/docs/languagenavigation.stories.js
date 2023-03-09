import "../../../site/main.scss";
import LanguageNavSingleLevel from "../singlelevel.hbs";
import LanguageNavMultiLevel from "../multilevel.hbs";
import LanguageNavNewspaper from "../language-navigation-newspaper.hbs";

export default {
  title: "Core Components/LanguageNavigation",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {},
};
const TemplateSingleLevel = ({ label, ...args }) => LanguageNavSingleLevel();
export const SingleLevel = TemplateSingleLevel.bind();

const TemplateMultiLevel = ({ label, ...args }) => LanguageNavMultiLevel();
export const MultiLevel = TemplateMultiLevel.bind();

const TemplateLanguageNavNewspaper = (args) => LanguageNavNewspaper({...args});
export const Newspaper = TemplateLanguageNavNewspaper.bind();
Newspaper.args = {
  variation: 'newspaper',
}
