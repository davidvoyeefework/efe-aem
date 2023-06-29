import Handlebars from 'handlebars/runtime.js';
import Article from '../article.hbs'
// import '../../../site/main.scss';

export default {
    title: 'Pages/Article',
    // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
    argTypes: {
    },
  };
  
  const TemplateArticle = ({ label, ...args }) => Article({...args});
  export const Primary = TemplateArticle.bind();
  Primary.args = {
   
  };
