import { defineStore } from 'pinia'
export const useStore = defineStore('id', {
  state: () => {
    return {
      token: '',
      source: '',
    }
  },
  actions: {
    SetToken(value: string) {
      this.token = value
    },
  },
})