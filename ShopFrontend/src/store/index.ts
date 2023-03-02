// stores/counter.js
import { defineStore } from 'pinia'
export const useCounterStore = defineStore('id', {
  state: () => {
    return {
      token: ''
    }
  },
  // could also be defined as
  // state: () => ({ count: 0 })
  actions: {
    SetToken(value: string) {
      this.token = value
    },
  },
})