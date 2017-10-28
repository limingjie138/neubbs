import * as types from '../constants/actionTypes'

const initialState = {
  profile: {},
  activate: true,
  error: '',
}

export default function (state = initialState, action) {
  const { type, payload, error } = action
  switch (type) {
    case types.REQUEST_SENDING: {
      return {
        ...state,
        error: '',
      }
    }
    case types.LOGIN_SUCCESS: {
      return {
        ...state,
        profile: {
          ...state.profile,
          ...payload,
        },
      }
    }
    case types.LOGOUT_SUCCESS: {
      return {
        ...state,
        profile: {},
        activate: true,
      }
    }
    case types.REGISTER_SUCCESS: {
      return {
        ...state,
        profile: {
          ...state.profile,
          ...payload,
        },
      }
    }
    case types.ACTIVATE_SUCCESS: {
      const { activate } = payload
      return {
        ...state,
        activate,
      }
    }
    case types.GET_PROFILE_SUCCESS: {
      return {
        ...state,
        profile: {
          ...state.profile,
          ...payload,
        },
      }
    }
    case types.REQUEST_ERROR: {
      return {
        ...state,
        error,
      }
    }
    default:
      return state
  }
}
