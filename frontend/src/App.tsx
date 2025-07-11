import { Provider } from 'react-redux';
import { store } from './store/store';
import AppRouter from './router/index';

function App() {
  return (
    <Provider store={store}>
      <AppRouter />
    </Provider>
  );
}

export default App;