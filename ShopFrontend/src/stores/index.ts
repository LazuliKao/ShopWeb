import { defineStore } from 'pinia'
export const useCounterStore = defineStore('id', {
  state: () => {
    return {
      token: '',
    }
  },
  actions: {
    SetToken(value: string) {
      this.token = value
    },
  },
})