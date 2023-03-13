import HandleBars from 'handlebars/runtime';
import List from '../list.hbs';
import ListWithTeaser from '../list-with-teaser.hbs';

HandleBars.registerPartial('List', List);
HandleBars.registerPartial('ListWithTeaser', ListWithTeaser);

export default {
  title: 'Core Components/List',
};

const FooterList = ({...args}) => List({...args});
export const footer = FooterList.bind({});
footer.args = {
  variation: 'footer',
};

const TemplateList = (args) => ListWithTeaser({...args});
export const listLayout = TemplateList.bind({});

listLayout.argTypes = {
  gap: {
    control: 'radio',
    options: ['tight', 'moderate', 'loose'],
  },
  variation: {
    control: 'radio',
    options: ['horizontal', 'vertical'],
  },
  alignment: {
    control: 'radio',
    options: ['left-align', 'right-align', 'center-align'],
  },
}

listLayout.args = {
  variation: 'horizontal',
  gap: 'moderate',
  alignment: 'left-align',
}
