Pod::Spec.new do |s|
  s.name             = 'RNCamera'
  s.version          = '1.4.1'
  s.summary          = 'react-native-camera'

  s.description      = <<-DESC
TODO: Add long description of the pod here.
                       DESC

  s.homepage         = 'https://github.com/shimo-react-native/react-native-camera'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'lisong' => 'lisong@shimo.im' }
  s.source           = { :git => 'https://github.com/shimo-react-native/react-native-camera.git', :tag => s.version.to_s }

  s.ios.deployment_target = '8.0'
  
  s.source_files = 'ios/**/*.{h,m,mm}'
  
  s.dependency 'React'

end
