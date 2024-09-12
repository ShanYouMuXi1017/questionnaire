const getters = {
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  userId: state => state.user.userId,
  roles: state => state.user.roles,
  permissions: state => state.user.permissions,
  nickName: state => state.user.nickName
}
export default getters
