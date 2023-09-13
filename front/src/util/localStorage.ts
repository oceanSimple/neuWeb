export const setObj = <T>(key: string, value: T) => {
  localStorage.setItem(key, JSON.stringify(value))
}

export const getObj = <T>(key: string) => {
  const value = localStorage.getItem(key)
  if (value) {
    return JSON.parse(value) as T
  }
  return null
}
