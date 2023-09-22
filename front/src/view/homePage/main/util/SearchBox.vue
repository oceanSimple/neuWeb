<!--代码来源：https://uiverse.io/satyamchaudharydev/plastic-bobcat-37-->
<template>
  <div id="container">
    <form class="form">
      <button>
        <svg
          aria-labelledby="search"
          fill="none"
          height="16"
          role="img"
          width="17"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9"
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="1.333"
          ></path>
        </svg>
      </button>
      <input
        v-model="inputValue"
        class="input"
        placeholder="搜索"
        type="text"
      />
      <button class="reset" type="reset">
        <svg
          class="h-6 w-6"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M6 18L18 6M6 6l12 12"
            stroke-linecap="round"
            stroke-linejoin="round"
          ></path>
        </svg>
      </button>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'

const inputValue = ref('')
</script>

<style lang="scss" scoped>
#container {
  margin-left: 40%;
  height: 50px;
  margin-top: 10px;
}

.form button {
  border: none;
  background: none;
  color: #8b8ba7;
}

/* styling of whole input container */
.form {
  --timing: 0.3s;
  --width-of-input: 300px;
  --height-of-input: 40px;
  --border-height: 2px;
  --input-bg: rgb(232, 237, 238);
  --border-color: #2f2ee9;
  --border-radius: 30px;
  --after-border-radius: 1px;
  position: relative;
  width: var(--width-of-input);
  height: var(--height-of-input);
  display: flex;
  align-items: center;
  padding-inline: 0.8em;
  border-radius: var(--border-radius);
  transition: border-radius 0.5s ease;
  background: var(--input-bg, #fff);
}

/* styling of Input */
.input {
  font-size: 0.9rem;
  background-color: transparent;
  width: 100%;
  height: 100%;
  padding-inline: 0.5em;
  padding-block: 0.7em;
  border: none;
}

/* styling of animated border */
.form:before {
  content: '';
  position: absolute;
  background: var(--border-color);
  transform: scaleX(0);
  transform-origin: center;
  width: 100%;
  height: var(--border-height);
  left: 0;
  bottom: 0;
  border-radius: 1px;
  transition: transform var(--timing) ease;
}

/* Hover on Input */
.form:focus-within {
  border-radius: var(--after-border-radius);
}

input:focus {
  outline: none;
}

/* here is code of animated border */
.form:focus-within:before {
  transform: scale(1);
}

/* styling of close button */
/* == you can click the close button to remove text == */
.reset {
  border: none;
  background: none;
  opacity: 0;
  visibility: hidden;
}

/* close button shown when typing */
input:not(:placeholder-shown) ~ .reset {
  opacity: 1;
  visibility: visible;
}

/* sizing svg icons */
.form svg {
  width: 17px;
  margin-top: 3px;
}
</style>
